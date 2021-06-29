import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SuperAdmin } from './super-admin.model';
import { SuperAdminPopupService } from './super-admin-popup.service';
import { SuperAdminService } from './super-admin.service';

@Component({
    selector: 'jhi-super-admin-dialog',
    templateUrl: './super-admin-dialog.component.html'
})
export class SuperAdminDialogComponent implements OnInit {

    superAdmin: SuperAdmin;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private superAdminService: SuperAdminService,
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
        if (this.superAdmin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.superAdminService.update(this.superAdmin));
        } else {
            this.subscribeToSaveResponse(
                this.superAdminService.create(this.superAdmin));
        }
    }

    private subscribeToSaveResponse(result: Observable<SuperAdmin>) {
        result.subscribe((res: SuperAdmin) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SuperAdmin) {
        this.eventManager.broadcast({ name: 'superAdminListModification', content: 'OK'});
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
    selector: 'jhi-super-admin-popup',
    template: ''
})
export class SuperAdminPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private superAdminPopupService: SuperAdminPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.superAdminPopupService
                    .open(SuperAdminDialogComponent as Component, params['id']);
            } else {
                this.superAdminPopupService
                    .open(SuperAdminDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
