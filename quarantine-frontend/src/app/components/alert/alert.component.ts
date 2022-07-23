import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
})
export class AlertComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}

@Component({
  selector: 'team-added',
  templateUrl: './team-added.html',
  styleUrls: ['./alert.component.css'],
})
export class TeamAdded implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}

@Component({
  selector: 'password-changed',
  templateUrl: './password-changed.html',
  styleUrls: ['./alert.component.css'],
})
export class PasswordChanged implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
