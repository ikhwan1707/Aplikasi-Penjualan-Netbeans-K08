/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author iLumniX
 */
// == Controllers == //
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import Controllers.JenisBarangController;
import Controllers.BarangController;
import Controllers.PetugasController;
import Controllers.DistributorController;
import Controllers.BarangMasukController;
import Controllers.PenjualanController;
// == Controllers == //

public class main {

    public static void main(String[] args) {
//      Memanggil Controller barang
        JenisBarangController jenis = new JenisBarangController();
        BarangController barang = new BarangController();
        PetugasController petugas = new PetugasController();
        DistributorController distributor = new DistributorController();
        BarangMasukController brgmasuk = new BarangMasukController();
        PenjualanController penjualan = new PenjualanController();

//      Memanggil Controller barang
//      Controller jenis
//          
//        System.out.print(jenis.autoIncrement());
//
//        System.out.println("");
//        for (String[] items : jenis.index()) {
//            System.out.print(Arrays.toString(items)+"\n");
//        }
//        System.out.println("");
//
//        jenis.store("Tamiya"+""+jenis.autoIncrement());
//        
//        String[] data = jenis.show("KJ001");
//        System.out.print("\n"+data[0]+"\n"+data[1]+"\n");
//
//        jenis.update("KJ001", "Pesawat"+jenis.autoIncrement());
//
//        jenis.delete("KJ001");
//      Controller jenis
//
//
//      Controller barang
//        System.out.print(barang.autoIncrement());
//
//        System.out.println("");
//        for (String[] items : barang.index()) {
//            System.out.print(Arrays.toString(items) + "\n");
//        }
//        System.out.println("");
//
//        barang.store("RTX"+barang.autoIncrement(), "KJ001", "10000", "20000", "20");
//
//        String[] data = barang.show("KB001");
//        System.out.print(
//                "\n\n\n" +
//                "Kode Barang\t: " + data[0] + "\n" +
//                "Nama Barang\t: " + data[1] + "\n" +
//                "Kode Jenis\t: " + data[2] + "\n" +
//                "Nama Jenis\t: " + data[3] + "\n" +
//                "Harga Neto\t: " + data[4] + "\n" +
//                "Harga Jual\t: " + data[5] + "\n" +
//                "Stok\t\t: " + data[6] + "\n" +
//                "\n\n\n" 
//        );
//
//        barang.update("KB001", "Skincare", "KJ002", "1000000", "1500000", "100");
//
//        barang.updateStok("KB001", "200");
//
//        barang.delete("KB004");
//      Controller barang
//
//      Controller petugas
//
//        System.out.print(petugas.autoIncrement());
//
//        System.out.println("");
//        for (String[] items : petugas.index()) {
//            System.out.print(Arrays.toString(items)+"\n");
//        }
//        System.out.println("");
//
//        petugas.store("Jainudin"+petugas.autoIncrement(), "Jl. Baru", "samsudin@id.com", "087698986787");
//
//        String[] data = petugas.show("KP001");
//        System.out.print(
//                "Kode Petugas\t: " + data[0] + "\n"
//                + "Nama Petugas\t: " + data[1] + "\n"
//                + "Alamat\t\t: " + data[2] + "\n"
//                + "Email\t\t: " + data[3] + "\n"
//                + "Telpon\t\t: " + data[4] + "\n"
//                + "\n"
//        );
//
//        petugas.update("KP001", "Samsudin", "Jl. Lama", "Jainudin@id.com", "098770908908");
//
//        petugas.delete("KP004");
//
//      Controller petugas
//
//      Controller distributor
//
//        System.out.print(distributor.autoIncrement());
//
//        System.out.println("");
//        for (String[] items : distributor.index()) {
//            System.out.print(Arrays.toString(items) + "\n");
//        }
//        System.out.println("");
//
//        distributor.store("Jamal"+distributor.autoIncrement(), "Jl Baru", "Antah Berantah", "Jamal"+distributor.autoIncrement()+"@id.com", "089709843456");
//
//        String[] data = distributor.show("KD001");
//        System.out.print(
//                "\n" + "Id Distributor\t\t: " + data[0] +
//                "\n" + "Nama Distributor\t: " + data[1] +
//                "\n" + "Alamat\t\t\t: " + data[2] +
//                "\n" + "Kota Asal\t\t: " + data[3] +
//                "\n" + "Email\t\t\t: " + data[4] +
//                "\n" + "Telpon\t\t\t: " + data[5]
//        );
//
//        distributor.update("KD001", "Samsudin"+distributor.autoIncrement(), "Jl. Lama", "Depok", "Samsudin"+distributor.autoIncrement(), "089765784589");
// 
//        distributor.delete("KD004");
//
//      Controller distributor
//
//      Controller barang masuk
//
//        System.out.print(brgmasuk.autoIncrement());
//
//        System.out.println("");
//        for (String[] items : brgmasuk.index()) {
//            System.out.println(Arrays.toString(items)+"\n");
//        }
//        System.out.println("");
//
//        Boolean check = brgmasuk.mencariNoNota("NN001");
//        if (check == true) {
//            String[] data = brgmasuk.show("NN001");
//            System.out.println(
//                    "\n" + "No Nota\t\t: " + data[0]
//                    + "\n" + "kode barang\t: "
//            );
//            for (String[] items : brgmasuk.showDetail(data[1])) {
//                System.out.print(Arrays.toString(items) + "\n");
//            }
//            System.out.print(
//                    "\n" + "tanggal masuk\t: " + data[2]
//                    + "\n" + "id Distributor\t: " + data[3]
//                    + "\n" + "id Petugas\t: " + data[4]
//                    + "\n" + "total\t\t: " + data[5]
//            );
//        } else {
//            System.out.print("tidak");
//        }
//
//        List<String[]> data = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            String[] detail = {"KB002", "RTXKB002", "1", "1000"};
//            data.add(detail);
//        }
//        brgmasuk.store(data, brgmasuk.autoIncrement(), "KP002", "KD002", "5000");
//
//        brgmasuk.delete("NN001");
//
//      Controller barang masuk
//
//      Controller penjualan   
//
//        System.out.print(penjualan.autoIncrement());
//        
//        System.out.println("");
//        for (String[] items : penjualan.index()) {
//            System.out.print(Arrays.toString(items) + "\n");
//        }
//        System.out.println("");
//
//        List<String[]> data = new ArrayList<>();
//        for (int i = 0; i < 5; i++ ) {
//            String kodeBarang = "KB002";
//            String namaBarang = "RTXKB002";
//            String jumlah = "10";
//            String subtotal = "1000";
//            String[] array = {kodeBarang, namaBarang, jumlah, subtotal};
//            data.add(array);
//        }
//        penjualan.store(data, penjualan.autoIncrement(), "2023-11-18", "KP005", "20000", "10000", "10000");
//
//        String[] data = penjualan.show("NF002");
//        System.out.print(
//                "\nNoFaktur\t: " + data[0] +
//                "\nTanggal\t\t: " + data[1] +
//                "\nNama Petugas\t: " + data[2] +
//                "\nBayar\t\t: " + data[3] +
//                "\nSisa\t\t: " + data[4] +
//                "\nTotal\t\t: " + data[5] + 
//                "\nKode Barang\t: " + data[6]  
//        );
//
        List<String[]> data = new ArrayList<>();
        String a = "KB001";
        String b = "KB002";
        String[] c = {a, b};
        data.add(c);
        for (String[] items : penjualan.validasi(data)) {
            System.out.print(items[0]);
        }
//
//      Controller penjualan   
    }
}
