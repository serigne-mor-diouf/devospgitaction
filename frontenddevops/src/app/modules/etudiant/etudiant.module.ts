import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { EtudiantDashboardComponent } from './components/etudiant-dashboard/etudiant-dashboard.component';
import { EtudiantComponent } from './components/etudiant/etudiant.component';
import { InscriptionComponent } from './components/inscription/inscription.component';
import { NoteComponent } from './components/note/note.component';
import { EtudiantRoutingModule } from './etudiant-routing.module';
import { SharedCoursModule } from '../cours/shared/shared-cours.module';


@NgModule({
  declarations: [
    EtudiantDashboardComponent,
    EtudiantComponent,
    InscriptionComponent,
    NoteComponent,
  ],
  imports: [
    CommonModule,
    EtudiantRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    SharedCoursModule
  ],
  exports: [
    EtudiantComponent,
    InscriptionComponent,
    NoteComponent,
    EtudiantDashboardComponent
  ]
})
export class EtudiantModule { }