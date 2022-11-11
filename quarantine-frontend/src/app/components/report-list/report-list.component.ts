import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ReportModel, RequestModel, UserModel } from 'src/app/model/userModel';
import { SelectionModel } from '@angular/cdk/collections';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-report-list',
  templateUrl: './report-list.component.html',
  styleUrls: ['./report-list.component.css']
})
export class ReportListComponent implements OnInit {
  @ViewChild('reportContent') reportContent: ElementRef;

  userLoggedIn: UserModel;
  users: UserModel[] = [];
  displayColumns = [
    'Avatar',
    'Reporter',
    'Reportee',
    'Ban',
    'isBanned',
    'Delete',
    'Delete Reportee',
    'View Report'
  ];
  loading: boolean = false;
  selection = new SelectionModel<UserModel>(true, []);
  reports: ReportModel[];

  constructor(
    private userService: UserService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.refreshUsers();
  }

  refreshUsers() {
    this.loading = true;
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
      this.refreshReports();
    });
    
  }

  refreshReports(){
    this.userService.getReports().subscribe(response=>{
      this.reports = response;
    })
  }
  onUserToggled(user: UserModel) {
    this.selection.toggle(user);
  }

  isAllSelected(): boolean {
    return this.selection.selected?.length == this.users.length;
  }

  deleteReport(id:number){
    this.userService.deleteReport(id).subscribe(response=>{
      this.refreshReports();
    });
    
  }

  deleteReportee(id:number){
    this.userService.deleteUser(id).subscribe(response=>{
      this.refreshReports();
    });
  }

  banReportee(id: number){
    this.userService.banReportee(id).subscribe(response=>{
      this.refreshReports();
    });
  }

  showReport(report:ReportModel){
    this.reportContent.nativeElement.innerHTML = report.report;
  }

  toggleAll() {
    if (this.isAllSelected) {
      this.selection.clear();
    } else {
      this.selection.select(...this.users);
    }
  }
}
