import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {CommonModule} from "@angular/common";
import {ButtonModule} from "primeng/button";
import {Tag} from "../tag/tag.model";
import {QuestionService} from "../../service/question.service";

@Component({
  selector: 'app-edit-question',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, ButtonModule],
  templateUrl: './edit-question.component.html',
  styleUrl: './edit-question.component.scss'
})
export class EditQuestionComponent implements OnInit {

  title: string = '';
  text: string = '';
  tagInput: string = '';
  tags: string[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private questionService: QuestionService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.title = params['title'];
      this.text = params['text'];
      this.tagInput = params['tags'];
      console.log('Tag input:', this.tagInput);
    });


  }

  updateQuestion() {
    const updatedQuestion = {
      title: this.title,
      text: this.text,
      tags: this.tags
    };

    const questionId = this.route.snapshot.queryParams['id'];
    console.log('Question ID:', questionId);

    this.questionService.updateQuestion(updatedQuestion, questionId).subscribe(
      (response) => {
        console.log('Question updated successfully:', response);
        this.router.navigate(['/question']);
      }
    );

    console.log('Update question');
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
