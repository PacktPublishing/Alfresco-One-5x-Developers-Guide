
import { provideRouter, RouterConfig } from '@angular/router';

import {
 SearchComponent,
 FilesComponent,
 ActivitiDemoComponent,
 ChartComponent,
    LoginDemoComponent
} from './components/index';

export const routes: RouterConfig = [
    { path: 'home', component: FilesComponent },
    { path: 'search', component: SearchComponent }, 
    { path: 'files', component: FilesComponent }, 
    { path: 'activiti', component: ActivitiDemoComponent }, 
    { path: 'chart', component: ChartComponent }, 
    { path: '', component: LoginDemoComponent },
    { path: 'login', component: LoginDemoComponent }
];

export const appRouterProviders = [
    provideRouter(routes)
];
