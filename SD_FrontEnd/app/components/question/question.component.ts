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
import {QuestionVote} from "./question-vote.model";
import {UserService} from "../../service/user.service";
import {User} from "../user/user.model";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  imports: [CommonModule, FormsModule, NgForOf, NgIf, RouterLink, NgOptimizedImage, ButtonModule, MatSnackBarModule],
  standalone: true,
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  questions: Question[] = [];
  protected loggedInUserId: number | undefined;
  points: number | undefined;

  searchText: string = '';
  searchTag: string = '';
  searchUser: string = '';

  loggedInUser: User = new User();

  constructor(private questionService: QuestionService, private router: Router, private imageService: ImageService, private userService: UserService) { }

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.loggedInUserId = parseInt(userId, 10);
    }

    if(!this.loggedInUserId) {
      this.logout();
    }

    this.userService.getUserById(this.loggedInUserId).subscribe((data: any) => {
      this.loggedInUser = data;
    });

    this.questionService.getQuestions().subscribe((data: any[]) => {
      this.questions = data.map(question => {
        const [year, month, day, hour, minute] = question.creationDateTime;
        const date = `${year}-${month}-${day}`;
        const time = `${hour}:${minute.toString().padStart(2, '0')}`;
        question.date = date;
        question.time = time;
        if (question.imagePath) {
          this.loadImage(question);
        }
        //this.getPoints(question.author.id);
        return question;
      });
    });

  }

  private loadImage(question: any) {
    this.imageService.getImage(question.imagePath).subscribe(blob => {
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

  editQuestion(question: Question) {
    const tagNames = question.tags.map(tag => tag.name);

    this.router.navigate(['/edit-question'], {
      queryParams: {
        id: question.id,
        title: question.title,
        text: question.text,
        imageUrl: question.imageUrl,
        imagePath: question.imagePath,
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

  upvoteQuestion(question: Question) {
    this.questionService.upVote(question.id, this.loggedInUserId).subscribe((response: string) => {
      alert(response);
    });
  }

  downvoteQuestion(question: Question) {
    this.questionService.downVote(question.id, this.loggedInUserId).subscribe((response: string) => {
      alert(response);
    });
  }

  getPoints(id: number) {
    this.userService.getPoints(id).subscribe((points: number) => {
      this.points = points;
    });
  }

  filterOwnQuestions() {
    this.questionService.getOwnQuestions(this.loggedInUserId).subscribe((data: any[]) => {
      this.questions = data.map(question => {
        const [year, month, day, hour, minute] = question.creationDateTime;
        const date = `${year}-${month}-${day}`;
        const time = `${hour}:${minute.toString().padStart(2, '0')}`;
        question.date = date;
        question.time = time;
        if (question.imagePath) {
          this.loadImage(question);
        }
        return question;
      });
    });
  }

  applyFilter() {
    if (this.searchText && this.searchTag || this.searchText && this.searchUser || this.searchTag && this.searchUser || this.searchText && this.searchTag && this.searchUser) {
      alert('Please provide only one filter');
      return;
    } else if(!this.searchText && !this.searchTag && !this.searchUser) {
      alert('Please provide a filter');
      return;
    } else {
      if(this.searchText) {
        this.questionService.getQuestionByText(this.searchText).subscribe((data: any[]) => {
          this.questions = data.map(question => {
            const [year, month, day, hour, minute] = question.creationDateTime;
            const date = `${year}-${month}-${day}`;
            const time = `${hour}:${minute.toString().padStart(2, '0')}`;
            question.date = date;
            question.time = time;
            if (question.imagePath) {
              this.loadImage(question);
            }
            return question;
          });
        });
      } else if (this.searchTag) {
        this.questionService.getQuestionByTag(this.searchTag).subscribe((data: any[]) => {
          this.questions = data.map(question => {
            const [year, month, day, hour, minute] = question.creationDateTime;
            const date = `${year}-${month}-${day}`;
            const time = `${hour}:${minute.toString().padStart(2, '0')}`;
            question.date = date;
            question.time = time;
            if (question.imagePath) {
              this.loadImage(question);
            }
            return question;
          });
        });
      } else if (this.searchUser) {
        this.questionService.getQuestionByUsername(this.searchUser).subscribe((data: any[]) => {
          this.questions = data.map(question => {
            const [year, month, day, hour, minute] = question.creationDateTime;
            const date = `${year}-${month}-${day}`;
            const time = `${hour}:${minute.toString().padStart(2, '0')}`;
            question.date = date;
            question.time = time;
            if (question.imagePath) {
              this.loadImage(question);
            }
            return question;
          });
        });
      }
    }
  }

}
