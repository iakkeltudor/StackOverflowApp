import {Component, NgModule, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { Question } from './question.model';
import {CommonModule, NgOptimizedImage} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {QuestionService} from "../../service/question.service";
import {ButtonModule} from "primeng/button";
import {Tag} from "../tag/tag.model";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {ImageService} from "../../service/image.service";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  imports: [CommonModule, FormsModule, NgForOf, NgIf, RouterLink, NgOptimizedImage, ButtonModule, MatSnackBarModule],
  standalone: true,
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {
  question1: Question | undefined;
  currentDate: string | undefined;
  currentTime: string | undefined;
  newAnswer: string | undefined;

  questions: Question[] = [];
  protected loggedInUserId: number | undefined;

  constructor(private questionService: QuestionService, private router: Router, private imageService: ImageService) { }

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.loggedInUserId = parseInt(userId, 10);
    }

    this.questionService.getQuestions().subscribe((data: any[]) => {
      this.questions = data.map(question => {
        console.log('Question:', question);
        const [year, month, day, hour, minute] = question.creationDateTime;
        const date = `${year}-${month}-${day}`;
        const time = `${hour}:${minute}`;
        question.date = date;
        question.time = time;
        if (question.imagePath) {
          this.loadImage(question);
        }
        return question;
      });
    });
  }

  private loadImage(question: any) {
    //console.log('serus');
    this.imageService.getImage(question.imagePath).subscribe(blob => {
      console.log('imagePath:', question.imagePath);
      const reader = new FileReader();
      reader.onload = (e: any) => {
        question.imageUrl = e.target.result;
      };
      reader.readAsDataURL(blob);
    });
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

  goToCreateQuestion() {
    this.router.navigate(['/create-question']);
  }

  submitAnswer(question: any) {
    const answer = this.newAnswer;
    console.log('Answer submitted:', question.answer);
    console.log('Question ID:', question.id);
    this.newAnswer = '';
  }

  editQuestion(question: Question) {
    const tagNames = question.tags.map(tag => tag.name);

    this.router.navigate(['/edit-question'], {
      queryParams: {
        id: question.id,
        title: question.title,
        text: question.text,
        tags: tagNames.join(',')
      }
    });
  }

  deleteQuestion(questionId: number) {
    if (confirm('Are you sure you want to delete this question?')) {
      this.questionService.deleteQuestion(questionId).subscribe(() => {
        this.questions = this.questions.filter(q => q.id !== questionId);
      });
    }
  }

  onMouseEnter(question: any) {
    question.hovered = true;
  }

  onMouseLeave() {
    this.questions.forEach(question => question.hovered = false);
  }

  goToAnswerPage(question: Question) {
    this.router.navigate(['/answer'], {
      queryParams: {
        question: JSON.stringify(question)
      }
    });
  }
}
