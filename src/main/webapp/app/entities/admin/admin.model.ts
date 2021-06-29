import { BaseEntity } from './../../shared';

export class Admin implements BaseEntity {
    constructor(
        public id?: number,
        public company?: BaseEntity,
    ) {
    }
}
