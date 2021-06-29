import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueueNSharedModule } from '../../shared';
import {
    AdminService,
    AdminPopupService,
    AdminComponent,
    AdminDetailComponent,
    AdminDialogComponent,
    AdminPopupComponent,
    AdminDeletePopupComponent,
    AdminDeleteDialogComponent,
    adminRoute,
    adminPopupRoute,
    AdminResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...adminRoute,
    ...adminPopupRoute,
];

@NgModule({
    imports: [
        QueueNSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AdminComponent,
        AdminDetailComponent,
        AdminDialogComponent,
        AdminDeleteDialogComponent,
        AdminPopupComponent,
        AdminDeletePopupComponent,
    ],
    entryComponents: [
        AdminComponent,
        AdminDialogComponent,
        AdminPopupComponent,
        AdminDeleteDialogComponent,
        AdminDeletePopupComponent,
    ],
    providers: [
        AdminService,
        AdminPopupService,
        AdminResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueueNAdminModule {}
