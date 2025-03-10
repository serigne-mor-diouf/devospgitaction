import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClasseDashboardComponent } from './components/classe-dashboard/classe-dashboard.component';
import { ClasseComponent } from './components/classe/classe.component';

const routes: Routes = [
  {
    path: '',
    component: ClasseDashboardComponent,
    children: [
      { path: '', redirectTo: 'liste', pathMatch: 'full' },
      { path: 'liste', component: ClasseComponent },
      { path: 'nouvelle', component: ClasseComponent },
      { path: 'stats', component: ClasseComponent },
      { path: 'parametres', component: ClasseComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClasseRoutingModule { }
