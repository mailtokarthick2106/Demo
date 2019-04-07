import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { Note } from '../note';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NotesService } from '../services/notes.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-edit-note-view',
  templateUrl: './edit-note-view.component.html',
  styleUrls: ['./edit-note-view.component.css']
})
export class EditNoteViewComponent implements OnInit, OnDestroy {
  note: Note;
  states: Array<string> = ['not-started', 'started', 'completed'];
  errMessage: string;
  constructor(private dialogRef: MatDialogRef<EditNoteViewComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any,
    private noteService: NotesService, private location: Location) { }

  ngOnInit() {    
    this.note = this.noteService.getNoteById(this.data.noteId);
  }
  deleteNote() {
     this.noteService.deleteNoteById(this.note.id);
     this.dialogRef.close();
   }
  onSave() {
    this.noteService.editNote(this.note).subscribe(editnote => {
      this.dialogRef.close();
    },
      error => {
        if (error.status === 404) {
          this.errMessage = error.message;
        } else {
          this.errMessage = 'An error occurred:' + error.error.message;
        }
      });
  }

  ngOnDestroy() {

  }

}
