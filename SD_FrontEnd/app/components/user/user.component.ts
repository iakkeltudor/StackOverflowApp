import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { ButtonModule } from 'primeng/button';
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {CommonModule, NgForOf, NgIf} from "@angular/common";
import {UserService} from "../../service/user.service";
import {User} from "./user.model";
import {QuestionService} from "../../service/question.service";
import {Question} from "../question/question.model";
import {ImageService} from "../../service/image.service";
import { MatDialog } from '@angular/material/dialog';
import { BanUserDialogComponent } from '../ban-user-dialog/ban-user-dialog.component';

@Component({
  selector: 'app-user',
  standalone: true,
  templateUrl: './user.component.html',
  imports: [ButtonModule, FormsModule, CommonModule, NgForOf, NgIf, RouterLink],
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(private dialog: MatDialog, private imageService: ImageService, private router: Router, private userService: UserService, private questionService: QuestionService) { }

  protected loggedInUserId: number | undefined;

  user: User = new User();
  questions: Question[] = [];
  allUsers: User[] = [];

  ngOnInit() {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.loggedInUserId = parseInt(userId, 10);
    }
    console.log("logged in user id: ", this.loggedInUserId);
    if(!this.loggedInUserId) {
      this.logout();
    }

    this.userService.getUserById(this.loggedInUserId).subscribe((data: any) => {
      this.user = data;
      console.log(this.user);

      if (this.user.role === 'ADMIN') {
        this.loadAllUsers();
      }
    });
    this.loadUserQuestions();
  }

  loadAllUsers() {
    this.userService.getAllUsers().subscribe((users: User[]) => {
      this.allUsers = users;
    });
  }

  loadUserQuestions() {
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

  openBanUserDialog(userId: number): void {
    const dialogRef = this.dialog.open(BanUserDialogComponent, {
      width: '400px',
      data: { userId }
    });

    dialogRef.afterClosed().subscribe(result => {
      // Handle dialog close event if needed
    });
  }

  unbanUser(userId: number): void {
    this.userService.unBanUser(userId).subscribe(() => {
      alert('User unbanned');
      this.loadAllUsers();
    });
  }
}
