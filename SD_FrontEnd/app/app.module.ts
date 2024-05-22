import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app.routes";
import {UserComponent} from "./components/user/user.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {QuestionComponent} from "./components/question/question.component";
import { HttpClientModule } from '@angular/common/http';
import {ButtonModule} from "primeng/button";
import {CommonModule} from "@angular/common";
import {CreateQuestionComponent} from "./components/create-question/create-question.component";
import {EditQuestionComponent} from "./components/edit-question/edit-question.component";
import {AnswerComponent} from "./components/answer/answer.component";

@NgModule({
  declarations: [
    UserComponent,
    RegisterComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ButtonModule,
    CommonModule,
    QuestionComponent,
    LoginComponent,
    CreateQuestionComponent,
    EditQuestionComponent,
    AnswerComponent
  ],
  providers: [],
  bootstrap: [UserComponent]

})
export class AppModule {
}
