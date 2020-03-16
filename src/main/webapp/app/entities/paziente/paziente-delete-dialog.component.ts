import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaziente } from 'app/shared/model/paziente.model';
import { PazienteService } from './paziente.service';

@Component({
  templateUrl: './paziente-delete-dialog.component.html'
})
export class PazienteDeleteDialogComponent {
  paziente?: IPaziente;

  constructor(protected pazienteService: PazienteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pazienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pazienteListModification');
      this.activeModal.close();
    });
  }
}
