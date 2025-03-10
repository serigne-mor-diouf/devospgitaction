import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WorkspaceComponent } from './components/workspace/workspace.component';

const routes: Routes = [
  { path: '', redirectTo: '/workspace', pathMatch: 'full' },
  { path: 'workspace', component: WorkspaceComponent },
  {
    path: 'cours',
    loadChildren: () => import('./modules/cours/cours.module').then(m => m.CoursModule)
  },
  {
    path: 'etudiants',
    loadChildren: () => import('./modules/etudiant/etudiant.module')
      .then(m => m.EtudiantModule),
    data: { title: 'Gestion des étudiants' }
  },
  {
    path: 'classes',
    loadChildren: () => import('./modules/classe/classe.module').then(m => m.ClasseModule)
  },
  {
    path: 'emploidutemp',
    loadChildren: () => import('./modules/emploidutemp/emploidutemp.module').then(m => m.EmploidutempModule)
  },
  {
    path: 'professeurs',
    loadChildren: () => import('./modules/professeur/professeur.module').then(m => m.ProfesseurModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
