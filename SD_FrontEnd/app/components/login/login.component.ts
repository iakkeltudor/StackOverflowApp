import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { ButtonModule } from "primeng/button";
import {LoginService} from "../../service/login.service";
import {Router} from "@angular/router";
import {QuestionService} from "../../service/question.service";
import {Question} from "../question/question.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [CommonModule, FormsModule, ButtonModule],
  standalone: true,
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @Output() submitEvent = new EventEmitter<{ username: string, password: string }>();

  constructor(private loginService: LoginService, private router: Router, private questionService: QuestionService) {

  }

  user = {username: '', password: ''};

  redirect() {
    this.router.navigate(['/question']);
  }

  sendCredentials() {
    console.log('sendCredentials method called');
    console.log('User object:', this.user);

    if (this.user.username.trim() !== '' && this.user.password.trim() !== '') {
      this.loginService.login(this.user.username, this.user.password).subscribe({
        next: (data: any) => {
          console.log('Login successful, token received:', data.token);
          this.redirect();
          localStorage.setItem('authToken', data.token);
          localStorage.setItem('userId', data.id);
          this.submitEvent.emit({ username: this.user.username, password: this.user.password });
        },
        error: (err) => {
          console.error('Login failed', err);
          alert('Username or password incorrect');
        }
      });
    } else {
      alert('Username and password must not be empty');
    }

  }
}
