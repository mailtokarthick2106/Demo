import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NoteComponent } from './note/note.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

// import {HttpModule} from '@angular/http';
// services
import { NotesService } from './services/notes.service';
import { CategoryService } from './services/category.service';
import { AuthenticationService } from './services/authentication.service';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RouterService } from './services/router.service';

// for routing
import { RouterModule, Routes } from '@angular/router';

// to guard our dashboard compoent
import { CanActivateRouteGuard } from './can-activate-route.guard';
import { NoteViewComponent } from './note-view/note-view.component';
import { ListViewComponent } from './list-view/list-view.component';
import { NoteTakerComponent } from './note-taker/note-taker.component';
import { MatDialogModule } from '@angular/material/dialog';
import { EditNoteViewComponent } from './edit-note-view/edit-note-view.component';
import { EditNoteOpenerComponent } from './edit-note-opener/edit-note-opener.component';
import { CategoryComponent } from './category/category.component';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'dashboard', component: DashboardComponent, canActivate: [CanActivateRouteGuard],
    children: [
      {
        path: 'view/notesview',
        component: NoteViewComponent
      },
      {
        path: 'view/listview',
        component: ListViewComponent
      },
      {
        path: 'view/editview',
        component: EditNoteViewComponent
      },
      {
        path: 'view/editcategory',
        component: CategoryComponent
      },
      {
        path: '',
        redirectTo: 'view/notesview',
        pathMatch: 'full'
      },
      {
        path: 'note/:noteId/edit',
        component: EditNoteOpenerComponent, outlet: 'noteEditOutlet'
      }
    ]
  },
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    DashboardComponent,
    NoteViewComponent,
    ListViewComponent,
    NoteTakerComponent,
    NoteComponent,
    EditNoteViewComponent,
    EditNoteOpenerComponent,
    CategoryComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatCardModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatSelectModule,
    // HttpModule,
    RouterModule.forRoot(appRoutes),
    MatDialogModule
  ],
  providers: [NotesService, AuthenticationService, CategoryService, RouterService, CanActivateRouteGuard],
  bootstrap: [AppComponent],
  entryComponents: [EditNoteViewComponent]
})
export class AppModule { }
