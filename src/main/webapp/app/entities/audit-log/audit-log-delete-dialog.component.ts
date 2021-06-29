import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuditLog } from './audit-log.model';
import { AuditLogPopupService } from './audit-log-popup.service';
import { AuditLogService } from './audit-log.service';

@Component({
    selector: 'jhi-audit-log-delete-dialog',
    templateUrl: './audit-log-delete-dialog.component.html'
})
export class AuditLogDeleteDialogComponent {

    auditLog: AuditLog;

    constructor(
        private auditLogService: AuditLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auditLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auditLogListModification',
                content: 'Deleted an auditLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-audit-log-delete-popup',
    template: ''
})
export class AuditLogDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auditLogPopupService: AuditLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auditLogPopupService
                .open(AuditLogDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
