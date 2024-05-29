import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [FormsModule, CommonModule],
  standalone: true
})
export class RegisterComponent {
  username: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  register() {
    if (this.password !== this.confirmPassword) {
      alert('Passwords do not match');
      return;
    }


    this.loginService.register(this.username, this.email, this.password).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Registration failed', err);
        alert('Registration failed');
      }
    });
  }
}
