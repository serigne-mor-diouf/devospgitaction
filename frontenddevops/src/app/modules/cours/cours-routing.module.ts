import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardCoursComponent } from './dashboard-cours/dashboard-cours.component';
import { CoursDetailComponent } from './cours-detail/cours-detail.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardCoursComponent
  },
  {
    path: ':id',
    component: CoursDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoursRoutingModule { }