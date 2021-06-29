import { BaseEntity } from './../../shared';

export class Window implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public currentAgent?: BaseEntity,
        public service?: BaseEntity,
    ) {
    }
}
