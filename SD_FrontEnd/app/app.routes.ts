import {RouterModule, Routes} from '@angular/router';
import {UserComponent} from "./components/user/user.component";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {QuestionComponent} from "./components/question/question.component";
import {CreateQuestionComponent} from "./components/create-question/create-question.component";
import {EditQuestionComponent} from "./components/edit-question/edit-question.component";
import {AnswerComponent} from "./components/answer/answer.component";
import {EditAnswerComponent} from "./components/edit-answer/edit-answer.component";

export const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full' },
  {path:"register", component: RegisterComponent},
  {path:"login", component: LoginComponent},
  {path:"user", component: UserComponent},
  {path:"question", component: QuestionComponent},
  {path:"create-question", component: CreateQuestionComponent},
  {path:"edit-question", component: EditQuestionComponent},
  {path:"answer", component: AnswerComponent},
  {path:"edit-answer", component: EditAnswerComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
