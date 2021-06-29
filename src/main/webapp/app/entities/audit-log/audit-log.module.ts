import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueueNSharedModule } from '../../shared';
import {
    AuditLogService,
    AuditLogPopupService,
    AuditLogComponent,
    AuditLogDetailComponent,
    AuditLogDialogComponent,
    AuditLogPopupComponent,
    AuditLogDeletePopupComponent,
    AuditLogDeleteDialogComponent,
    auditLogRoute,
    auditLogPopupRoute,
    AuditLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...auditLogRoute,
    ...auditLogPopupRoute,
];

@NgModule({
    imports: [
        QueueNSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuditLogComponent,
        AuditLogDetailComponent,
        AuditLogDialogComponent,
        AuditLogDeleteDialogComponent,
        AuditLogPopupComponent,
        AuditLogDeletePopupComponent,
    ],
    entryComponents: [
        AuditLogComponent,
        AuditLogDialogComponent,
        AuditLogPopupComponent,
        AuditLogDeleteDialogComponent,
        AuditLogDeletePopupComponent,
    ],
    providers: [
        AuditLogService,
        AuditLogPopupService,
        AuditLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueueNAuditLogModule {}
