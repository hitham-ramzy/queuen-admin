import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuditLog } from './audit-log.model';
import { AuditLogService } from './audit-log.service';

@Component({
    selector: 'jhi-audit-log-detail',
    templateUrl: './audit-log-detail.component.html'
})
export class AuditLogDetailComponent implements OnInit, OnDestroy {

    auditLog: AuditLog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auditLogService: AuditLogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuditLogs();
    }

    load(id) {
        this.auditLogService.find(id).subscribe((auditLog) => {
            this.auditLog = auditLog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuditLogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auditLogListModification',
            (response) => this.load(this.auditLog.id)
        );
    }
}
