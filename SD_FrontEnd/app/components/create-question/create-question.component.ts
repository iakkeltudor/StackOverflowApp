import {Component, OnInit} from '@angular/core';
import { ButtonModule } from "primeng/button";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import { QuestionService } from "../../service/question.service";
import { LoginService } from "../../service/login.service";
import { DatePipe } from '@angular/common';
import {HttpClient} from "@angular/common/http";
import {ImageService} from "../../service/image.service";
import {Tag} from "../tag/tag.model";
import {Observable} from "rxjs";
import {response} from "express";
import {Location} from "@angular/common";

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  imports: [CommonModule, FormsModule, RouterLink, ButtonModule],
  standalone: true,
  styleUrls: ['./create-question.component.scss'],
  providers: [DatePipe]

})
export class CreateQuestionComponent implements OnInit {
  title: string = '';
  text: string = '';
  imagePath: string = '';
  tags: Tag[] = [];
  tagInput: string = '';
  picture: File | null = null;

  loggedInUserId: string = localStorage.getItem('userId') || '';
  ngOnInit() {
    if(!this.loggedInUserId) {
      this.logout();
    }
  }

  constructor(
    private router: Router,
    private questionService: QuestionService,
    private imageService: ImageService,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private http: HttpClient,
    private location: Location
  ) {}

  onSubmit() {

    this.addTag();

    if (!this.title || !this.text || !this.tags.length || !this.picture) {
      alert('Please fill in all required fields.');
      return;
    }

    const currentDate = new Date();
    const creationDateTime = this.datePipe.transform(
      currentDate,
      'yyyy-MM-dd HH:mm'
    );

    const questionData = {
      title: this.title,
      text: this.text,
      imagePath: this.imagePath,
      tags: this.tags.map(tag => tag.name),
      authorId: this.loggedInUserId,
      creationDateTime: creationDateTime,
    };

    this.onUpload();

    this.questionService.createQuestion(questionData).subscribe(
      (response) => {
        this.title = '';
        this.text = '';
        this.imagePath = '';
        this.tags = [];
        this.tagInput = '';
      }
    );
    this.location.back();
  }

  onFileSelected(event: any) {
    this.picture = event.target.files[0];
    // @ts-ignore
    this.imagePath = this.picture.name;

  }

  addTag() {
    const newTagNames = this.tagInput.split(',');

    newTagNames.forEach(newTagName => {
      if (newTagName.trim() !== '') {
        const newTag: Tag = { name: newTagName.trim() };
        this.tags.push(newTag);
      }
    });

    this.tagInput = '';
  }

  onUpload() {
    if (this.picture) {
      this.imageService.uploadImage(this.picture).subscribe();
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
