import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {

  constructor(private router: Router) { }

  // users: any[] = [
  //   {
  //     username: 'user1',
  //     questions: [
  //       {
  //         author: 'User 1',
  //         title: 'User 1 Question 1',
  //         body: 'This is the body of User 1 Question 1',
  //         creationTime: '10:00 AM',
  //         creationDate: '2024-04-15'
  //       },
  //       {
  //         author: 'User 1',
  //         title: 'User 1 Question 2',
  //         body: 'This is the body of User 1 Question 2',
  //         creationTime: '11:00 AM',
  //         creationDate: '2024-04-15'
  //       },
  //       // Add more sample questions for user1 as needed
  //     ]
  //   },
  //   {
  //     username: 'user2',
  //     questions: [
  //       {
  //         author: 'User 2',
  //         title: 'User 2 Question 1',
  //         body: 'This is the body of User 2 Question 1',
  //         creationTime: '12:00 PM',
  //         creationDate: '2024-04-15'
  //       },
  //       {
  //         author: 'User 2',
  //         title: 'User 2 Question 2',
  //         body: 'This is the body of User 2 Question 2',
  //         creationTime: '01:00 PM',
  //         creationDate: '2024-04-15'
  //       },
  //       // Add more sample questions for user2 as needed
  //     ]
  //   },
  //   // Add more hard-coded users as needed
  // ];

  user: any = {
    username: 'user1',
    question: {
      author: 'User 1',
      title: 'User 1 Question',
      body: 'This is the body of User 1 Question',
      creationTime: '10:00 AM',
      creationDate: '2024-04-15'
    }
  };

  logout() {
    this.router.navigate(['/login']);
  }
  goToUser() {
    this.router.navigate(['/user']);
  }

  goToHome() {
    this.router.navigate(['/question']);
  }
}
