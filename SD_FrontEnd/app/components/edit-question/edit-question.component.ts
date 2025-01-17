import { Component, OnInit } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { CommonModule, DatePipe } from "@angular/common";
import { ButtonModule } from "primeng/button";
import { QuestionService } from "../../service/question.service";
import { ImageService } from "../../service/image.service";
import {Tag} from "../tag/tag.model";
import {Location} from "@angular/common";

@Component({
  selector: 'app-edit-question',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, ButtonModule],
  templateUrl: './edit-question.component.html',
  styleUrls: ['./edit-question.component.scss'],
  providers: [DatePipe]
})
export class EditQuestionComponent implements OnInit {

  title: string = '';
  text: string = '';
  tagInput: string = '';
  tags: Tag[] = [];
  imagePath: string = '';
  imageUrl: string = '';
  newImage: File | null = null;
  creationDateTime: string | null = '';
  loggedInUserId: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private questionService: QuestionService,
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
      this.title = params['title'];
      this.text = params['text'];
      this.tagInput = params['tags'];
      this.imagePath = params['imagePath'];
      this.imageUrl = params['imageUrl'];

      if (!Array.isArray(this.tags)) {
        this.tags = [];
      } else {
        this.tags = this.tags.map((tag: any) => {
          if (typeof tag === 'string') {
            return { name: tag };
          } else {
            return tag;
          }
        });
      }

      // Join existing tags into a string separated by commas
      this.tagInput = this.tags.map(tag => tag.name).join(', ');
    });
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length) {
      this.newImage = event.target.files[0];
    }
  }

  updateQuestion() {

    this.addTag();

    if(!this.title || !this.text || !this.tags.length) {
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

    const updatedQuestion = {
      title: this.title,
      text: this.text,
      tags: this.tags.map(tag => tag.name), // Convert tags to array of strings
      authorId: localStorage.getItem('userId') || '',
      imagePath: this.imagePath,
      creationDateTime: this.creationDateTime
    };

    const questionId = this.route.snapshot.queryParams['id'];

    this.questionService.updateQuestion(updatedQuestion, questionId).subscribe(
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

  addTag() {
    const newTagNames = this.tagInput.split(',').map(tag => tag.trim());
    newTagNames.forEach(newTagName => {
      if (newTagName !== '') {
        // Check if the tag already exists
        if (!this.tags.some(tag => tag.name === newTagName)) {
          // Add the new tag
          this.tags.push({ name: newTagName });
          this.tagInput = ''; // Clear the tag input field after adding
        } else {
          alert('Tag already exists.');
        }
      }
    });
  }
}
