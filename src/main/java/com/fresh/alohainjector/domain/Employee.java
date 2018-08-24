package com.fresh.alohainjector.domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Employee")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
    @Id
    @Type(type = "uuid-char")
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Column(name = "Id" , columnDefinition="uniqueidentifier")
    private UUID id;

    private Integer number;
    private String firstName;
    private String lastName;

    private int disabilityStatus; // 0
    private boolean doNotImportTimePunches; // False
    private int contactRelationship; // 0
    private int employmentReason; // 0
    private boolean exemptionFromWithholding; // False
    private int federalAllowances; // 0
    private float federalWithholding; // 0
    private int federalMaritalStatus; // 0
    private int stateAllowances; // 0
    private float stateWithholding; //0
    private int stateTaxStatus; //0
    private int localAllowances; //0
    private float localWithholding; //0
    private int citizenStatus; //0
    private int documentATitle; //0
    private int documentBTitle; //0
    private int documentCTitle; //0
    private boolean i9HardCopy; //false
    private String liquorCertificationNumber; // ""
    private int a4AnnualIncome; //0
    private int a4MoreThanWithholding; //0
    private int a4LessThanWithholding; //0
    private int languagePreference;//0
    private String WorkPermitNumber;//""
    private int VacationPayPercent;//0
    private int BankNumber;//0
    private int BranchNumber;//0
    private String AccountNumber;// ""
    private int PersonalTaxCredit;//0
    private float AdditionalFederalTax;//0
    private float AdditionalProvinceTax;//0
    private int ProvinceExempt;//0
    private int K4AllowanceRate;//0
    private int K4NumberAllowances;//0
    private float K4Withholding;//0
    private boolean K4ExemptFromWithholding;//false
    private String EmailAddress;//""
    private String SmsAddress;// ""
    private String BOHPasswordHash;//""
    private boolean UseDallasKey;//false
    private String PasswordHash;//""
    private String MagcardPasswordHash;//""
    private String DallasKeyPasswordHash;//""
    private int FailedLoginCount;//0
    private String DataVersion;//
    private String FK_Owner;//


}
