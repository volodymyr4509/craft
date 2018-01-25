/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CraftTestModule } from '../../../test.module';
import { DogDialogComponent } from '../../../../../../main/webapp/app/entities/dog/dog-dialog.component';
import { DogService } from '../../../../../../main/webapp/app/entities/dog/dog.service';
import { Dog } from '../../../../../../main/webapp/app/entities/dog/dog.model';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('Dog Management Dialog Component', () => {
        let comp: DogDialogComponent;
        let fixture: ComponentFixture<DogDialogComponent>;
        let service: DogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CraftTestModule],
                declarations: [DogDialogComponent],
                providers: [
                    UserService,
                    DogService
                ]
            })
            .overrideTemplate(DogDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DogDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Dog(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.dog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Dog();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.dog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
