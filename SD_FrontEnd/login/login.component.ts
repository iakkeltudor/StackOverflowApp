import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{

  username: string = '';
  password: string = '';

  constructor() {
  }

  onSubmit(event: Event) {
    event.preventDefault();
    return false;
  }

  @Input() testInput:any = "No input received!"

  @Output() nameEmitter = new EventEmitter<string>()

  user1: { username: string, password: string } = { username: '', password: '' };

}
