import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EtudiantComponent } from './components/etudiant/etudiant.component';
import { EtudiantDashboardComponent } from './components/etudiant-dashboard/etudiant-dashboard.component';
import { InscriptionComponent } from './components/inscription/inscription.component';
import { NoteComponent } from './components/note/note.component';

const routes: Routes = [
  {
    path: '',
    component: EtudiantDashboardComponent,
    children: [
      {
        path: '',
        redirectTo: 'liste',
        pathMatch: 'full'
      },
      {
        path: 'liste',
        component: EtudiantComponent,
        data: { title: 'Liste des étudiants' }
      },
      {
        path: 'inscriptions',
        component: InscriptionComponent,
        data: { title: 'Gestion des inscriptions' }
      },
      {
        path: 'notes',
        component: NoteComponent,
        data: { title: 'Gestion des notes' }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EtudiantRoutingModule { }
