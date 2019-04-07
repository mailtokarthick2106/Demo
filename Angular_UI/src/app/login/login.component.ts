import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { RouterService } from '../services/router.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public bearerToken: any;
  loading = false;
  returnUrl: String;
  submitMessage: String;
  passwordRegex = '/^(?=.[A-Za-z])(?=.\d)[A-Za-z\d]{4,20}/';
  username = new FormControl('', [Validators.required, Validators.maxLength(15)]);
  password = new FormControl('', Validators.compose([Validators.required,
    Validators.minLength(4), Validators.maxLength(20), Validators.pattern(this.passwordRegex)]));

  constructor(private _authService: AuthenticationService, public routerService: RouterService) { }

  ngOnInit() {
  }

  /*   username: any;
     password: any;
    userForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl('')
    }); */

  loginSubmit() {
    /*  console.log('Userane :'+this.userForm.get('username').value);
     console.log('Pwd :'+this.userForm.get('password').value);
        this.username = this.userForm.get('username').value;
        this.password = this.userForm.get('password').value; */
    // console.log('Userane :'+this.username.value);
    // console.log('Pwd :'+this.password.value);
    this.loading = true;
    this._authService.authenticateUser({ 'userId': this.username.value, 'userPassword': this.password.value }).subscribe(
      res => {
        console.log(res['token']);
        this.bearerToken = res['token'];
        console.log(this.username.value);
        this._authService.setUserId(this.username.value);
        this._authService.setBearerToken(this.bearerToken);
        this.routerService.routeToDashboard();
      },
      error => {
        if (error.status === 404) {
          this.submitMessage = `Http failure response for ${error.url}: 404 Not Found`;
        } else {
          this.submitMessage = 'Please provide valid inputs';
        }
      });
  }
}
