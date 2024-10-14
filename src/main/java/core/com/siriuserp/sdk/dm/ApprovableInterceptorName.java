package com.siriuserp.sdk.dm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 *
 * Note:
 * Interceptor digunakan untuk proses - proses tambahan yang di perlukan pada saat proses approvable di jalankan.
 * contoh:
 * 1.Jika dokument yg akan di approve merupakan dokumen standard sales order maka di perlukan pemrosesan lebih lanjut
 *   agar dokumen standard sales order yg di maksud dapat di buat delivery planning.Untuk kasus ini yaitu dengan cara
 *   mengganti flag enabled dari standard sales order menjadi true.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="approvable_interceptor_name")
public class ApprovableInterceptorName {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_approvable")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Approvable approvable;

}
