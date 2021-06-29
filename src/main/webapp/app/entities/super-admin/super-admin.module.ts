import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueueNSharedModule } from '../../shared';
import {
    SuperAdminService,
    SuperAdminPopupService,
    SuperAdminComponent,
    SuperAdminDetailComponent,
    SuperAdminDialogComponent,
    SuperAdminPopupComponent,
    SuperAdminDeletePopupComponent,
    SuperAdminDeleteDialogComponent,
    superAdminRoute,
    superAdminPopupRoute,
    SuperAdminResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...superAdminRoute,
    ...superAdminPopupRoute,
];

@NgModule({
    imports: [
        QueueNSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SuperAdminComponent,
        SuperAdminDetailComponent,
        SuperAdminDialogComponent,
        SuperAdminDeleteDialogComponent,
        SuperAdminPopupComponent,
        SuperAdminDeletePopupComponent,
    ],
    entryComponents: [
        SuperAdminComponent,
        SuperAdminDialogComponent,
        SuperAdminPopupComponent,
        SuperAdminDeleteDialogComponent,
        SuperAdminDeletePopupComponent,
    ],
    providers: [
        SuperAdminService,
        SuperAdminPopupService,
        SuperAdminResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueueNSuperAdminModule {}
