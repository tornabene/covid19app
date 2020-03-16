import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19AppSharedModule } from 'app/shared/shared.module';
import { FarmacoComponent } from './farmaco.component';
import { FarmacoDetailComponent } from './farmaco-detail.component';
import { FarmacoUpdateComponent } from './farmaco-update.component';
import { FarmacoDeleteDialogComponent } from './farmaco-delete-dialog.component';
import { farmacoRoute } from './farmaco.route';

@NgModule({
  imports: [Covid19AppSharedModule, RouterModule.forChild(farmacoRoute)],
  declarations: [FarmacoComponent, FarmacoDetailComponent, FarmacoUpdateComponent, FarmacoDeleteDialogComponent],
  entryComponents: [FarmacoDeleteDialogComponent]
})
export class Covid19AppFarmacoModule {}
