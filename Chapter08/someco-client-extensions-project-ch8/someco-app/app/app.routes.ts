
import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard, AuthGuardEcm, AuthGuardBpm } from 'ng2-alfresco-core';

import {
  AboutComponent,
  HomeComponent,
 SearchComponent,
 FilesComponent,
 ActivitiDemoComponent,
  ActivitiAppsView,
  FormViewer,
  FormNodeViewer,
  LoginDemoComponent,
  SettingComponent,
  BBCComponent
} from './components/index';

export const appRoutes: Routes = [
  {path: 'login', component: LoginDemoComponent},
  {
    path: '',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  
  {
    path: 'files',
    component: FilesComponent,
    canActivate: [AuthGuardEcm]
  },
  {
    path: 'files/:id',
    component: FilesComponent,
    canActivate: [AuthGuardEcm]
  },
  
  
  {
    path: 'search',
    component: SearchComponent,
    canActivate: [AuthGuardEcm]
  },
  
  
  {
    path: 'activiti',
    component: ActivitiAppsView,
    canActivate: [AuthGuardBpm]
  },
  {
    path: 'activiti/apps',
    component: ActivitiAppsView,
    canActivate: [AuthGuardBpm]
  },
  {
    path: 'activiti/apps/:appId/tasks',
    component: ActivitiDemoComponent,
    canActivate: [AuthGuardBpm]
  },
  {
    path: 'activiti/appId/:appId',
    component: ActivitiDemoComponent,
    canActivate: [AuthGuardBpm]
  },
  {
    path: 'activiti/tasks/:id',
    component: FormViewer,
    canActivate: [AuthGuardBpm]
  },
  {
    path: 'activiti/tasksnode/:id',
    component: FormNodeViewer,
    canActivate: [AuthGuardBpm]
  },
  
  {path: 'about', component: AboutComponent},
  {path: 'settings', component: SettingComponent},
  {path: 'bbc', component: BBCComponent}

];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
