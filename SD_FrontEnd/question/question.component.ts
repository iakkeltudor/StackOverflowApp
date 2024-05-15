import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Question } from './question.model'; // Assuming you have a Question model

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {
  question1: Question | undefined; // Assuming you have a Question model
  currentDate: string | undefined;
  currentTime: string | undefined;
  newAnswer: string | undefined;

  hardcodedQuestions: Question[] = [
    {
      title: 'Sample Question 1',
      author: 'John Doe',
      body: 'This is a sample question body 1.',
      creationDate: '2024-04-15', // Assuming you have creation date available
      creationTime: '10:00 AM' // Assuming you have creation time available
    },
    {
      title: 'Sample Question 2',
      author: 'Jane Smith',
      body: 'This is a sample question body 2.',
      creationDate: '2024-04-16', // Assuming you have creation date available
      creationTime: '11:00 AM' // Assuming you have creation time available
    },
    {
      title: 'Sample Question 3',
      author: 'Alice Johnson',
      body: 'This is a sample question body 3.',
      creationDate: '2024-04-17', // Assuming you have creation date available
      creationTime: '12:00 PM' // Assuming you have creation time available
    }
  ];

  constructor(private router: Router) { }

  ngOnInit(): void {
    // Initialize your question here (e.g., fetch it from a service)
    this.question1 = {
      title: 'Sample Question',
      author: 'John Doe',
      body: 'This is a sample question body.',
      creationDate: '2024-04-15', // Assuming you have creation date available
      creationTime: '10:00 AM' // Assuming you have creation time available
    };

    // Get current date and time
    const currentDateTime = new Date();
    this.currentDate = currentDateTime.toLocaleDateString();
    this.currentTime = currentDateTime.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
  }

  logout() {
    this.router.navigate(['/login']);
  }

  goToUser() {
    this.router.navigate(['/user']);
  }

  goToHome() {
    this.router.navigate(['/question']);
  }

  submitAnswer() {
    // Add your answer submission logic here
    const answer = this.newAnswer;
    // Assuming you want to log the answer for now
    console.log('Answer submitted:', answer);
    // You can then clear the answer field if needed
    this.newAnswer = '';
  }
}
