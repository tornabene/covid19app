import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFarmaco } from 'app/shared/model/farmaco.model';
import { FarmacoService } from './farmaco.service';

@Component({
  templateUrl: './farmaco-delete-dialog.component.html'
})
export class FarmacoDeleteDialogComponent {
  farmaco?: IFarmaco;

  constructor(protected farmacoService: FarmacoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.farmacoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('farmacoListModification');
      this.activeModal.close();
    });
  }
}
