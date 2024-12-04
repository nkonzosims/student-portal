import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthService, RegistrationInt, VerificationInt} from '../../../services/auth/auth.service';
import {HttpClient} from '@angular/common/http';
import {NgClass} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.scss',
  providers: [HttpClient]
})
export class RegistrationComponent implements OnInit{

  formBuilder = inject(FormBuilder);
  authService = inject(AuthService);
  router = inject(Router);

  registrationForm = this.formBuilder.group({
    firstName: [''],
    lastName: [''],
    dateOfBirth: [''],
    email: [''],
    password: [''],
    phoneNumber: [''],
    idNumber: [''],
    passportNumber: [''],
    studentNumber: ['']
  });

  verificationForm = this.formBuilder.group({
    email: [''],
    verificationCode: ['']
  })

  constructor() { }

  ngOnInit() {

  }

  register() {
    // const registrationData: RegistrationInt = this.registrationForm.value as RegistrationInt;
    const res = this.authService.register(this.registrationForm.value as any);

    res.subscribe(response => {
      console.log('Registration response', response);
    }, error => {
      console.log('Registration error', error);
    })
  }

  verifyAccount() {

    const verificationData: VerificationInt = {
      email: this.registrationForm.get('email')?.getRawValue(),
      verificationCode: this.verificationForm.get('verificationCode')?.getRawValue()
    }

    this.authService.verifyAccount(verificationData).subscribe((response: any) => {
      this.router.navigate(['/dashboard']);

    }, (error: any) => {
      console.log('Verification error', error);
    })
  }
}
