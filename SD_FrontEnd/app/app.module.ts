import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from "@angular/core";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app.routes";
import {UserComponent} from "../user/user.component";
import {LoginComponent} from "../login/login.component";
import {RegisterComponent} from "../register/register.component";
import {QuestionComponent} from "../question/question.component";
import { HttpClientModule } from '@angular/common/http';
import {ButtonModule} from "primeng/button";

@NgModule({
  declarations: [
    UserComponent,
    LoginComponent,
    RegisterComponent,
    QuestionComponent
  ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        FormsModule,
        HttpClientModule,
        ButtonModule
    ],
  providers: [],
  bootstrap: [UserComponent]

})
export class AppModule {
}
