import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { QueueNSuperAdminModule } from './super-admin/super-admin.module';
import { QueueNAdminModule } from './admin/admin.module';
import { QueueNAgentModule } from './agent/agent.module';
import { QueueNCustomerModule } from './customer/customer.module';
import { QueueNCompanyModule } from './company/company.module';
import { QueueNBranchModule } from './branch/branch.module';
import { QueueNBranchServiceModule } from './branch-service/branch-service.module';
import { QueueNQueueModule } from './queue/queue.module';
import { QueueNWindowModule } from './window/window.module';
import { QueueNSessionModule } from './session/session.module';
import { QueueNAuditLogModule } from './audit-log/audit-log.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        QueueNSuperAdminModule,
        QueueNAdminModule,
        QueueNAgentModule,
        QueueNCustomerModule,
        QueueNCompanyModule,
        QueueNBranchModule,
        QueueNBranchServiceModule,
        QueueNQueueModule,
        QueueNWindowModule,
        QueueNSessionModule,
        QueueNAuditLogModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueueNEntityModule {}
