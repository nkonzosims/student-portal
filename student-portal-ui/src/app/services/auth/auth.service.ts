import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {of} from 'rxjs';
import {LoggedInUserSession} from '../../pages/auth/login/login.component';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  httpClient = inject(HttpClient);
  userToken = signal<string | null>(null);

  constructor() { }

  register(registrationData: RegistrationInt) {
    return this.httpClient.post('http://localhost:8081/api/auth/register', registrationData);
  }

  login(loginData: LoginInt) {
    return this.httpClient.post<LoggedInUserSession>('http://localhost:8081/api/auth/login', loginData);
  }

  verifyAccount(verificationData: VerificationInt) {
    return this.httpClient.post('http://localhost:8081/api/auth/verify', verificationData);
  }

  getProfile() {

    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.userToken());
    return this.httpClient.get(
      'http://localhost:8081/api/student/profile',
      { headers }
    );
  }

  updateProfile(profileData: UpdateProfileInt) {
    return this.httpClient.put('http://localhost:8081/api/student/profile/update', profileData);
  }

}

export interface LoginInt {
  email: string;
  password: string;
}

export interface VerificationInt {
  email: string;
  verificationCode: string;
}

export interface RegistrationInt {
  firstName: string;
  lastName: string;
  dateOfBirth: Date;
  email: string;
  password: string;
  phoneNumber: string;
  idNumber?: string;
  passportNumber?: string;
}

export interface UpdateProfileInt {
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  email: string;
  password: string;
  phoneNumber: string;
  studentNumber: string;
  idNumber?: string;
  passportNumber?: string;
}
