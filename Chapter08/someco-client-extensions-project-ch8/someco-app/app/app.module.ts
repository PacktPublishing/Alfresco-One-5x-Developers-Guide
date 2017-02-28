
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { CoreModule } from 'ng2-alfresco-core';
import { DataTableModule } from 'ng2-alfresco-datatable';
import { SearchModule } from 'ng2-alfresco-search';
import { DocumentListModule } from 'ng2-alfresco-documentlist';
import { UploadModule } from 'ng2-alfresco-upload';
import { ActivitiFormModule } from 'ng2-activiti-form';
import { ActivitiTaskListModule } from 'ng2-activiti-tasklist';
import { ActivitiProcessListModule } from 'ng2-activiti-processlist';
import { AnalyticsModule } from 'ng2-activiti-analytics';
import { LoginModule } from 'ng2-alfresco-login';
import { UserInfoComponentModule } from 'ng2-alfresco-userinfo';
import { ViewerModule } from 'ng2-alfresco-viewer';
import { AppComponent } from './app.component';
import { routing } from './app.routes';

import {
  HomeComponent,
  SearchComponent,
  SearchBarComponent,
  ActivitiDemoComponent,
  ActivitiAppsView,
  FormViewer,
  FormNodeViewer,
  FilesComponent,
  AboutComponent,
  LoginDemoComponent,
  SettingComponent,
  BBCComponent
} from './components/index';

@NgModule({
    imports: [
        BrowserModule,
        routing,
        CoreModule.forRoot(),
        DataTableModule,
        SearchModule.forRoot(), 
        
        DocumentListModule.forRoot(),
        UploadModule.forRoot(),
        ViewerModule.forRoot(),
        ActivitiFormModule.forRoot(),
        ActivitiTaskListModule.forRoot(),
        ActivitiProcessListModule.forRoot(),
        AnalyticsModule.forRoot(),
        LoginModule,
        UserInfoComponentModule.forRoot()
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        SearchBarComponent,
        SearchComponent,
        ActivitiDemoComponent,
        ActivitiAppsView,
        FormViewer,
        FormNodeViewer,
        FilesComponent,
        AboutComponent,
        LoginDemoComponent,
        SettingComponent,
        BBCComponent
    ],
    providers: [],
    bootstrap: [ AppComponent ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule { }
