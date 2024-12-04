import {Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import {AuthService, LoginInt} from '../../../services/auth/auth.service';
import {RouterLink} from '@angular/router';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    NgClass
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  formBuilder = inject(FormBuilder);
  authService = inject(AuthService);

  loginForm = this.formBuilder.group({
    email: [''],
    password: ['']
  });

  login() {
    const loginData: LoginInt = this.loginForm.value as LoginInt;

    console.log('loginData on login', this.loginForm.value);

    this.authService.login(loginData).subscribe(res => {
        console.log('Login response', res);},
      error => {
        console.log('Login error', error);
      })
  }
}
