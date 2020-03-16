import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19AppSharedModule } from 'app/shared/shared.module';
import { PazienteComponent } from './paziente.component';
import { PazienteDetailComponent } from './paziente-detail.component';
import { PazienteUpdateComponent } from './paziente-update.component';
import { PazienteDeleteDialogComponent } from './paziente-delete-dialog.component';
import { pazienteRoute } from './paziente.route';

@NgModule({
  imports: [Covid19AppSharedModule, RouterModule.forChild(pazienteRoute)],
  declarations: [PazienteComponent, PazienteDetailComponent, PazienteUpdateComponent, PazienteDeleteDialogComponent],
  entryComponents: [PazienteDeleteDialogComponent]
})
export class Covid19AppPazienteModule {}
