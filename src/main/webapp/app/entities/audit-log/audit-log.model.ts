import { BaseEntity } from './../../shared';

export class AuditLog implements BaseEntity {
    constructor(
        public id?: number,
    ) {
    }
}
