import { Component, OnInit } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { CommonModule, DatePipe } from "@angular/common";
import { ButtonModule } from "primeng/button";
import { QuestionService } from "../../service/question.service";
import { ImageService } from "../../service/image.service";
import {Tag} from "../tag/tag.model";
import {AnswerService} from "../../service/answer.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-edit-answer',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, ButtonModule],
  templateUrl: './edit-answer.component.html',
  styleUrls: ['./edit-answer.component.scss'],
  providers: [DatePipe]
})
export class EditAnswerComponent implements OnInit {

  title: string = '';
  text: string = '';
  tagInput: string = '';
  tags: Tag[] = [];
  imagePath: string = '';
  imageUrl: string = '';
  newImage: File | null = null;
  creationDateTime: string | null = '';
  questionId: string = '';
  loggedInUserId: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private answerService: AnswerService,
    private imageService: ImageService,
    private datePipe: DatePipe,
    private location: Location
  ) {
    this.tags = [];
  }

  ngOnInit(): void {
    this.loggedInUserId = localStorage.getItem('userId') || '';
    if(!this.loggedInUserId) {
      this.logout();
    }
    this.route.queryParams.subscribe(params => {
      this.text = params['text'];
      this.imagePath = params['imagePath'];
      this.imageUrl = params['imageUrl'];
      this.questionId = params['questionId'];
    });

  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length) {
      this.newImage = event.target.files[0];
    }
  }

  updateAnswer() {
    if(!this.text) {
      alert('Please fill in all required fields.');
      return;
    }

    const currentDate = new Date();
    this.creationDateTime = this.datePipe.transform(
      currentDate,
      'yyyy-MM-dd HH:mm'
    );

    if (this.newImage) {
      this.imagePath = this.newImage.name;
    }

    const updatedAnswer = {
      text: this.text,
      questionId: this.questionId,
      authorId: localStorage.getItem('userId') || '',
      imagePath: this.imagePath,
      creationDateTime: this.creationDateTime
    };

    const answerId = this.route.snapshot.queryParams['id'];

    this.answerService.updateAnswer(updatedAnswer, answerId).subscribe(
      (response) => {
        if (this.newImage) {
          this.uploadImage();
        }
        this.location.back();
      }
    );
  }

  uploadImage() {
    if (this.newImage) {
      this.imageService.uploadImage(this.newImage).subscribe(() => {
        this.router.navigate(['/question']);
      });
    }
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

}
