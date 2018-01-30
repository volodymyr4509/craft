/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { CraftTestModule } from '../../../test.module';
import { VoteComponent } from '../../../../../../main/webapp/app/entities/vote/vote.component';
import { VoteService } from '../../../../../../main/webapp/app/entities/vote/vote.service';
import { Vote } from '../../../../../../main/webapp/app/entities/vote/vote.model';

describe('Component Tests', () => {

    describe('Vote Management Component', () => {
        let comp: VoteComponent;
        let fixture: ComponentFixture<VoteComponent>;
        let service: VoteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CraftTestModule],
                declarations: [VoteComponent],
                providers: [
                    VoteService
                ]
            })
            .overrideTemplate(VoteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Vote(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.votes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
