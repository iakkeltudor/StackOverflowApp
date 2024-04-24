import {RouterModule, Routes} from '@angular/router';
import {UserComponent} from "../user/user.component";
import {NgModule} from "@angular/core";
import {LoginComponent} from "../login/login.component";
import {RegisterComponent} from "../register/register.component";
import {QuestionComponent} from "../question/question.component";

export const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full' },
  {path:"register", component: RegisterComponent},
  {path:"login", component: LoginComponent},
  {path:"user", component: UserComponent},
  {path:"question", component: QuestionComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
