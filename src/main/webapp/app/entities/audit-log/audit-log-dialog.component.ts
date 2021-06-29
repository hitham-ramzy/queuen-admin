import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuditLog } from './audit-log.model';
import { AuditLogPopupService } from './audit-log-popup.service';
import { AuditLogService } from './audit-log.service';

@Component({
    selector: 'jhi-audit-log-dialog',
    templateUrl: './audit-log-dialog.component.html'
})
export class AuditLogDialogComponent implements OnInit {

    auditLog: AuditLog;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auditLogService: AuditLogService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auditLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auditLogService.update(this.auditLog));
        } else {
            this.subscribeToSaveResponse(
                this.auditLogService.create(this.auditLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuditLog>) {
        result.subscribe((res: AuditLog) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuditLog) {
        this.eventManager.broadcast({ name: 'auditLogListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-audit-log-popup',
    template: ''
})
export class AuditLogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auditLogPopupService: AuditLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auditLogPopupService
                    .open(AuditLogDialogComponent as Component, params['id']);
            } else {
                this.auditLogPopupService
                    .open(AuditLogDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
