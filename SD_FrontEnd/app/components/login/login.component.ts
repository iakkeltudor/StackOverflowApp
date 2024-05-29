import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { ButtonModule } from "primeng/button";
import {LoginService} from "../../service/login.service";
import {Router, RouterLink} from "@angular/router";
import {QuestionService} from "../../service/question.service";
import {Question} from "../question/question.model";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [CommonModule, FormsModule, ButtonModule, RouterLink],
  standalone: true,
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @Output() submitEvent = new EventEmitter<{ username: string, password: string }>();

  constructor(private userService: UserService, private loginService: LoginService, private router: Router, private questionService: QuestionService) {

  }

  username: string = '';
  password: string = '';
  banned: number = 0;

  redirect() {
    this.router.navigate(['/question']);
  }

  async sendCredentials() {
    if (this.username.trim() !== '' && this.password.trim() !== '') {
      await this.checkBan(this.username);
      console.log('banned: ', this.banned);
      if (this.banned === 0) {
        this.loginService.login(this.username, this.password).subscribe({
          next: (data: any) => {
            console.log('Login successful, token received:', data.token);
            this.redirect();
            localStorage.setItem('authToken', data.token);
            localStorage.setItem('userId', data.id);
            this.submitEvent.emit({ username: this.username, password: this.password });
          },
          error: (err) => {
            console.error('Login failed', err);
            alert('Username or password incorrect');
          }
        });
      } else {
        alert('You are banned');
      }
    } else {
      alert('Username and password must not be empty');
    }
  }

  checkBan(username: string): Promise<void> {
    return new Promise((resolve, reject) => {
      this.userService.checkBan(username).subscribe({
        next: (data: any) => {
          this.banned = data;
          resolve();
        },
        error: (err) => {
          console.error('Error checking ban status', err);
          reject(err);
        }
      });
    });
  }
}
