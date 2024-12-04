import { Routes } from '@angular/router';
import {RegistrationComponent} from './pages/auth/registration/registration.component';
import {LoginComponent} from './pages/auth/login/login.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {AuthGuard} from './services/auth/auth.guard';
import {ProfileComponent} from './pages/profile/profile.component';
import {ProjectsComponent} from './pages/projects/projects.component';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegistrationComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'projects',
    component: ProjectsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '**',
    redirectTo: ''
  }
];
