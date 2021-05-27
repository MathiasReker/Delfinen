```mermaid
classDiagram
Main --> App
App --> MenuController
AddResultMenuAction --> CompetionController
AnonymizeMenuAction --> MemberController
CompetitionSubMenuMenuAction --> MenuController
CreateCompetitionSubMenuMenuAction --> CompetitionController
CreateMemberMenuAction --> MemberController
DisplayArrearsMenuAction --> PaymentController
EditMemberMenuAction --> MemberController
HandlePaymentMenuAction --> PaymentController
MemberSubMenuMenuAction --> MenuController
PaymentRequestMenuAction --> MemberController
PaymentSubMenuMenuAction --> MenuController
PredictIncomeDefinedMenuAction --> PredictionController
PredictIncomeMenuAction --> PredictionController
RenewalRequestMenuAction --> MeberController
SearchMemberMenuAction --> MemberController
TopResultsMenuAction --> LeaderController
ViewMemberMenuAction --> MemberController
ViewResultsMenuAction --> CompetitionController



MemberController o-- MemberView
MemberController o-- MembershipController
MemberController --> MemberModel
MemberController o-- PaymentRequestService
MemberController o-- MemberService

PaymentController o-- MemberController
PaymentController o-- PaymentService
DisciplineController --> DisciplineModel
PredictionController o-- PredictionView
PredictionController o-- MemberController

CompetitionController --o DisciplineModel
CompetitionController o-- CompetitionView
LeaderboardController --o CompetitionView
PaymentController o-- PaymentView
CompetitionController o-- MemberController
CompetitionController --> CompetitionModel
CompetitionController o-- CompetitionService
DisciplineController --> DisciplineModel
LeaderboardController --o CompetitionController
LeaderboardController --> CompetitionModel
LeaderboardController --> ResultModel
MemberController --> MembershipModel
MembershipController --> MembershipModel
MembershipController --> MemberModel
MenuController o-- MenuView
MenuController --> MenuModel

InputModel --> MemberModel
InputModel --> CompetitionModel
CompetitionModel --> ResultModel
DisciplineModel o-- DistanceType
DisciplineModel o-- StyleType
MemberModel --> MembershipModel
MembershipModel --> DisciplineModel
PricingModel --> MemberModel
ResultModel --> MemberModel
ResultModel --> DisciplineModel
ResultModel --> CompetitionModel


InputController <.. CompetitionController
InputController <.. LeaderboardController
InputController <.. MemberController
InputController <.. DisciplineController
InputController <.. PredictionController
InputController <.. MenuController


CompetitionService --> FileService
MemberService --> FileService
PaymentRequestService --> FileService
PaymentService --> FileService
CompetionService ..o CompetitionModel
MemberService ..o MemberModel
PaymentRequestService ..o MemberModel

CompetitionView --|> View
InputView --|> View
MemberView --|> View
MenuView --|> View
PaymentView --|> View
PredictionView --|> View
View --> ColorTextType
View --> ColorKeyType


class CompetitionController{
    -CompetitionView
    -MemberController
    -Arraylist<CompetitionModel>
    -CompetitionService
    +createNewCompetition()
    +addResultToCompetition()
    -addResultTime(member, competition)
    +viewCompetitionResults()
    -arrayWithResultToDisplay(result) String [][]
    +getMember(id) MemberModel
    +addResultToCompetition(competition, resultModel)
    +styleToArray() String []
    +distanceToArray(style, gender) String []
    +saveCompetitions()
    -toArraylist(competitions []) Arraylist<Competionsmodel>
    -generateId() String
    -getCompetitionHeader() String[]
    +getColumnWidth() int[]
    -getCompetitionLine(competition) String[]
    +viewTableCompetitions()
}

class DisciplineController {
    -ArrayList<DisciplineModel>
    +chosenDiscipline(gender, style) ArrayList<DisciplineModel>
}
class InputController{
    -Scanner
    -InputView
    +promptYesNo() boolean
    +validateInteger() int
    +validateOptionRange(max) int
    +validateName() String
    +validateMail() String
    +validateDate() String
    +validatePhoneNumber() String
    +validateMemberId(members) String
    +validateCompetitionsId(competitions) CompetitionModel
    +validateCompetitionResultTime() String
    +validateCompetitionTime() String
    +validatePlacement() String
    +anyString() String
}
class LeaderboardController{
    -CompetitionView
    -CompetitionController
    -ArrayList<CompetitionModel>
    -findTop(style, distance, amount) ResultModel[]
    -findDiscipline(style, distance) ArrayList<ResultModel>
    -arrayWithResultToDisplay(resultTimes) String[][]
    +displayTop5Results()

}
class MemberController{
    -MemberView
    -MembershipController
    -ArrayList<MemberModel>
    +createMember()
    +addMember(id, name, mail, gender, birthday, phone, competitive, active)
    -gendersToArray() String[]
    -renewExpiringMembers()
    +requestPaymentForUnpaidMembers()
    ~removeMemberFromList(members) ArrayList<MemberModel>
    +getMemberByID(id, members) MenuModel
    +getMemberByID(id) MenuModel
    +saveMembers()
    +loadMembers() MemberModel[]
    +membersToStringArray(members) ArrayList<MemberModel>
    -getMemberLine(member) String[]
    -getMemberHeader() String[]
    +viewTableMembers(member)
    +viewTableMembers(members)
    +viewTableMembers()
    +getColumnWidth() int[]
    +searchMember()
    +viewMemberById()
    +getMemberById(id) MemberModel
    +viewMembersByName()
    +getMembersByName(name) ArrayList<MemberModel>
    +viewMembersByMail()
    +getMembersByMail(mail) ArrayList<MemberModel>
    +viewMemberByPhoneNumber()
    +getMemberByPhoneNumber(phoneNumber) ArrayList<MemberModel>
    +anonymizeMember()
    +editMember()
    +generateMemberId() String
    +getExpiringMembers(memberModels,days) ArrayList<MemberModel>
    +getUnpaidMembers(ArrayList<MemberModel> memberModels) ArrayList<MemberModel>
}
class MembershipController{
  +addActiveMembership(member)
  +addPassiveMembership(member)
  -generateMembershipId(member) String
  +renewMembership(member, durationYears)
  -createNewMembership() MembershipModel
}
class MenuController{
    -MenuView
    -MenuModel
    +Run()
}
class PaymentController{
    -MemberController
    -PaymentView
    -ArrayList<String>
    -PaymentService
    +updateMemberShip(members)
    -reviewPaymentFile()
    +handlePayments()
    -getValidPayments() ArrayList<MemberModel>
    -createBackupFile(failedPayments)
    +displayMembersInArrear()
    +findMembersInArrear(members) ArrayList<MemberModel>
    +calcPeriod(date1, date2) String
}
class PredictionController{
    -PredictionView
    -MemberController
    +predictIncome()
    +predictIncome(days)
    +predictIncomeInXDays(days) int
}
class CompetitionModel{
    -id:String
    -startDate:LocalDate
    -name:String
    -startTime:LocalTime
    -results:ArrayList<ResultModel>

}
class DisciplineModel{
    -distance:DistanceType
    -style:StyleType
}
class InputModel{
  +isValidDate(date)$ boolean
  +isValidCompetitionTime(time)$ boolean
  +isValidCompetitionResultTime(time)$ boolean
  +isValidName(text)$ boolean
  +isValidMail(text)$ boolean
  +isValidPhoneNumber(text)$ boolean
  +isValidRange(in, min, max)$ boolean
  +isValidMemberId(in, members)$ boolean
  +isValidCompetitionId(in, competitions)$ boolean

}
class MemberModel{
    -id:String
    -name:String
    -Create_Date:LocalDate
    -birthdate:LocalDate
    -gender:GenderType
    -phonenumber:String
    -mail:String
    -ageGroup:String
    -competitive:boolean
    -disciplines:ArrayList<DisciplineModel>
    -memberships:ArrayList<MembershipModel>
    -deleted:boolean
    +getAgeGroup() String
    +getLatestMembership() MembershipModel
    +getAge() int
}
class MembershipModel{
    -id:String
    -creationDate:LocalDate
    -startingDate:LocalDate
    -expirtingDate:LocalDate
    -paid:boolean
    -active:boolean
}
class MenuModel{
    -MENU_ACTIONS:MenuAction[]
    -MENU_HEADER:String
    -LOAD_TEXT:String
}
class PricingModel{
  +calculateMemberPrice(member) int
}
class ResultModel{
    -member:MemberModel
    -resultTime:LocalTime
    -discipline:DisciplineModel
    -competition:CompetitionModel
    -placement:String
}
class DistanceType
class StyleType
class GenderType
<<Enumaration>> StyleType
<<Enumaration>> DistanceType
<<Enumaration>> GenderType

class CompetitionView{
  +displayCompetitionResults(competitionResults)
  +displayTopResults(competitionResults)
  +displayCompetition(competition, columnWidth)

}
class InputView
class MemberView{
  displayMember(member, columnWidth)
}
class MenuView{
  +printMenuOptions(header, menuAction)
}
class PaymentView{
  +displayPayments(payments)
  +displayArrears(arrears)
}
class PredictionView
class View
<<abstract>> View

class App
class Main

class ColorKeyType
<<Enumaration>> ColorKeyType
class ColorTextType

class CompetitionService{
    -FILE_SERVICE:FileService
}
class ConfigService{
    -KEY:String
}
class FileService{
    -FILE:File
    -PATH:String
}
class MemberService{
    -FILE_SERVICE:FileService
}
class PaymentRequestService{
    -FILE_SERVICE:FileService
}
class PaymentService{
    -FILE_SERVICE:FileService
}


class AddResultMenuAction
class AnonymizeMenuAction
class CompetitionSubMenuMenuAction
class CreateCompetitionSubMenuMenuAction
class CreateMemberMenuAction
class DisplayArrearsMenuAction
class EditMemberMenuAction
class ExitMenuAction
class HandlePaymentMenuAction
class MemberSubMenuMenuAction
class MenuAction
class PaymentRequestMenuAction
class PaymentSubMenuMenuAction
class PredictIncomeDefinedMenuAction
class PredictIncomeMenuAction
class RenewalRequestMenuAction
class SearchMemberMenuAction
class TopResultsMenuAction
class ViewMemberMenuAction
class ViewResultsMenuAction


```
```mermaid
classDiagram
AddResultMenuAction --> CompetionController
AnonymizeMenuAction --> MemberController
CompetitionSubMenuMenuAction --> MenuController
CreateCompetitionSubMenuMenuAction --> CompetitionController
CreateMemberMenuAction --> MemberController
DisplayArrearsMenuAction --> PaymentController
EditMemberMenuAction --> MemberController
HandlePaymentMenuAction --> PaymentController
MemberSubMenuMenuAction --> MenuController
PaymentRequestMenuAction --> MemberController
PaymentSubMenuMenuAction --> MenuController
PredictIncomeDefinedMenuAction --> PredictionController
PredictIncomeMenuAction --> PredictionController
RenewalRequestMenuAction --> MeberController
SearchMemberMenuAction --> MemberController
TopResultsMenuAction --> LeaderController
ViewMemberMenuAction --> MemberController
ViewResultsMenuAction --> CompetitionController
AddResultMenuAction --|> MenuAction
AnonymizeMenuAction --|> MenuAction
CompetitionSubMenuMenuAction --|> MenuAction
CreateCompetitionSubMenuMenuAction --|> MenuAction
CreateMemberMenuAction --|> MenuAction
DisplayArrearsMenuAction --|> MenuAction
EditMemberMenuAction --|> MenuAction
ExitMenuAction --|> MenuAction
HandlePaymentMenuAction --|> MenuAction
MemberSubMenuMenuAction --|> MenuAction
PaymentRequestMenuAction --|> MenuAction
PaymentSubMenuMenuAction --|> MenuAction
PredictIncomeDefinedMenuAction --|> MenuAction
PredictIncomeMenuAction --|> MenuAction
RenewalRequestMenuAction --|> MenuAction
SearchMemberMenuAction --|> MenuAction
TopResultsMenuAction --|> MenuAction
ViewMemberMenuAction --|> MenuAction
ViewResultsMenuAction --|> MenuAction

```
