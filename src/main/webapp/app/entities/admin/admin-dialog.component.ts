import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Admin } from './admin.model';
import { AdminPopupService } from './admin-popup.service';
import { AdminService } from './admin.service';
import { Company, CompanyService } from '../company';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-admin-dialog',
    templateUrl: './admin-dialog.component.html'
})
export class AdminDialogComponent implements OnInit {

    admin: Admin;
    isSaving: boolean;

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private adminService: AdminService,
        private companyService: CompanyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService
            .query({filter: 'admin-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.admin.company || !this.admin.company.id) {
                    this.companies = res.json;
                } else {
                    this.companyService
                        .find(this.admin.company.id)
                        .subscribe((subRes: Company) => {
                            this.companies = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.admin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adminService.update(this.admin));
        } else {
            this.subscribeToSaveResponse(
                this.adminService.create(this.admin));
        }
    }

    private subscribeToSaveResponse(result: Observable<Admin>) {
        result.subscribe((res: Admin) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Admin) {
        this.eventManager.broadcast({ name: 'adminListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-admin-popup',
    template: ''
})
export class AdminPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adminPopupService: AdminPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adminPopupService
                    .open(AdminDialogComponent as Component, params['id']);
            } else {
                this.adminPopupService
                    .open(AdminDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
