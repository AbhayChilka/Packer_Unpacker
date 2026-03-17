import java.util.*;
import java.io.*;

class PackerUnpacker
{
    public static void Pack(String FolderName, String PackedFile) throws Exception
    {
        File fobj = new File(FolderName);

        if(!fobj.exists())
        {
            System.out.println("Directory not found");
            return;
        }

        FileOutputStream foobj = new FileOutputStream(PackedFile);

        File[] Arr = fobj.listFiles();
        int iCount = 0;

        for(File file : Arr)
        {
            if(file.isFile())
            {
                String Header = file.getName() + "|" + file.length() + "|";

                // Make header fixed size (100 bytes)
                StringBuilder sb = new StringBuilder(Header);
                while(sb.length() < 100)
                {
                    sb.append(" ");
                }

                foobj.write(sb.toString().getBytes());

                FileInputStream fiobj = new FileInputStream(file);

                byte[] Buffer = new byte[1024];
                int iRet = 0;

                while((iRet = fiobj.read(Buffer)) != -1)
                {
                    foobj.write(Buffer, 0, iRet);
                }

                fiobj.close();
                iCount++;
                System.out.println("Packed: " + file.getName());
            }
        }

        foobj.close();

        System.out.println("Packing completed. Files packed: " + iCount);
    }

    public static void Unpack(String PackedFile) throws Exception
    {
        File fobj = new File(PackedFile);

        if(!fobj.exists())
        {
            System.out.println("Packed file not found");
            return;
        }

        FileInputStream fiobj = new FileInputStream(fobj);

        byte[] Header = new byte[100];
        int iCount = 0;

        while(fiobj.read(Header) > 0)
        {
            String HeaderX = new String(Header).trim();

            if(HeaderX.length() == 0) break;

            String[] Tokens = HeaderX.split("\\|");

            if(Tokens.length < 2)
            {
                System.out.println("Corrupted header");
                break;
            }

            String FileName = Tokens[0];
            int FileSize = Integer.parseInt(Tokens[1]);

            File newFile = new File(FileName);

            if(newFile.exists())
            {
                System.out.println("File already exists: " + FileName);
                continue;
            }

            FileOutputStream foobj = new FileOutputStream(newFile);

            byte[] Buffer = new byte[1024];
            int remaining = FileSize;

            while(remaining > 0)
            {
                int readBytes = fiobj.read(Buffer, 0, Math.min(Buffer.length, remaining));
                foobj.write(Buffer, 0, readBytes);
                remaining -= readBytes;
            }

            foobj.close();
            iCount++;
            System.out.println("Unpacked: " + FileName);
        }

        fiobj.close();

        System.out.println("Unpacking completed. Files unpacked: " + iCount);
    }

    public static void main(String Arg[]) throws Exception
    {
        Scanner sobj = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("------- Packer Unpacker Tool -------");
        System.out.println("-----------------------------------------------------");

        while(true)
        {
            System.out.println("\n1. Pack Directory");
            System.out.println("2. Unpack File");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sobj.nextInt();
            sobj.nextLine();

            switch(choice)
            {
                case 1:
                    System.out.print("Enter directory name: ");
                    String dir = sobj.nextLine();

                    System.out.print("Enter packed file name: ");
                    String packFile = sobj.nextLine();

                    Pack(dir, packFile);
                    break;

                case 2:
                    System.out.print("Enter packed file name: ");
                    String unpackFile = sobj.nextLine();

                    Unpack(unpackFile);
                    break;

                case 3:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}